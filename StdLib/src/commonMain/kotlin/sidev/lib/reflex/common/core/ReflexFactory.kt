package sidev.lib.reflex.common.core

import sidev.lib.platform.Platform
import sidev.lib.platform.platform
import sidev.lib.property.reevaluateLazy
import sidev.lib.reflex.common.*
import sidev.lib.reflex.common.SiCallableImpl
import sidev.lib.reflex.common.SiMutableProperty1Impl
import sidev.lib.reflex.common.SiProperty1Impl
import sidev.lib.reflex.common.native.*


internal expect val SiNativeWrapper.nativeInnerName: String?
internal expect val SiNativeWrapper.nativeFullName: String?
internal expect val SiNativeWrapper.nativeSimpleName: String?

private val SiNativeWrapper.qualifiedNativeName: String
    get()= nativeFullName ?: NativeReflexConst.TEMPLATE_NATIVE_NAME


internal fun createNativeWrapper(native: Any): SiNativeWrapper = object : SiNativeWrapper{
    override val implementation: Any = native
}


fun SiClassifier.createType(
    arguments: List<SiTypeProjection> = emptyList(),
    nullable: Boolean = false
): SiType = ReflexFactory.createType(
    if(descriptor.native != null) createNativeWrapper(descriptor.native!!) else null,
    this, arguments, nullable
)

val SiClass<*>.startProjectedType: SiType
    get()= createType(typeParameters.map { SiTypeProjection.STAR })

object ReflexFactory{
    fun createType(
        nativeCounterpart: SiNativeWrapper?,
        classifier: SiClassifier?,
        arguments: List<SiTypeProjection> = emptyList(),
        nullable: Boolean = false,
        modifier: Int= 0
    ): SiType {
        val typeParam= if(classifier is SiClass<*>) classifier.typeParameters
            else emptyList()
        if(arguments.size < typeParam.size)
            throw IllegalArgumentException("arguments.size: ${arguments.size} < typeParam.size: ${typeParam.size}.")
        return object : SiTypeImpl() {
            override val descriptor: SiDescriptor = createDescriptor(nativeCounterpart = nativeCounterpart, modifier = modifier)
            override val arguments: List<SiTypeProjection> = arguments
            override val classifier: SiClassifier? = classifier
            override val isMarkedNullable: Boolean = nullable
        }
    }


    fun createParameter(
        nativeCounterpart: SiNativeWrapper?, //Untuk mengakomodasi parameter setter dan getter.
        hostCallable: SiCallable<*>?,
        index: Int, type: SiType,
        name: String?= null,
        kind: SiParameter.Kind= SiParameter.Kind.VALUE,
        defaultValue: Any?= null,
        modifier: Int= 0
    ): SiParameter = object: SiParamterImpl() {
        override val descriptor: SiDescriptor = createDescriptor(hostCallable, nativeCounterpart, modifier)
        override val index: Int = index
        override val name: String? = name ?: nativeCounterpart?.qualifiedNativeName
        override val isOptional: Boolean = SiModifier.isOptional(this)
        override val isVararg: Boolean = SiModifier.isVararg(this)
        override var type: SiType = type
        override val kind: SiParameter.Kind = kind
        override val defaultValue: Any? = defaultValue
    }

    internal fun createParameterLazyly(
        nativeCounterpart: SiNativeWrapper?, //Untuk mengakomodasi parameter setter dan getter.
        hostCallable: SiCallable<*>?,
        index: Int, //type: SiType,
        name: String?= null,
        kind: SiParameter.Kind= SiParameter.Kind.VALUE,
        defaultValue: Any?= null,
        modifier: Int= 0
    ): SiParameter = object: SiParamterImpl() {
        override val descriptor: SiDescriptor = createDescriptor(hostCallable, nativeCounterpart, modifier)
        override val index: Int = index
        override val name: String? = name ?: nativeCounterpart?.qualifiedNativeName
        override val isOptional: Boolean = SiModifier.isOptional(this)
        override val isVararg: Boolean = SiModifier.isVararg(this)
        override val type: SiType by reevaluateLazy { eval ->
            val type= if(nativeCounterpart != null) getReturnType(nativeCounterpart.implementation)
                else ReflexTemplate.typeDynamic.also { eval.value= true; return@reevaluateLazy it }
            eval.value= platform != Platform.JS || isTypeFinal(type.descriptor.native!!)
            type
        }
        override val kind: SiParameter.Kind = kind
        override val defaultValue: Any? = defaultValue
    }


    fun createTypeParameter(
        nativeCounterpart: SiNativeWrapper,
        host: SiReflex?,
        upperBounds: List<SiType>, variance: SiVariance,
        modifier: Int= 0
    ): SiTypeParameter = object: SiTypeParameterImpl() {
        override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart, modifier)
        override val name: String = nativeCounterpart.qualifiedNativeName
        override val upperBounds: List<SiType> = upperBounds
        override val variance: SiVariance = variance
    }

    fun <R> createCallable(
        nativeCounterpart: SiNativeWrapper,
        host: SiReflex?= null,
        returnType: SiType= ReflexTemplate.typeAnyNullable,
        parameters: List<SiParameter> = emptyList(),
        typeParameters: List<SiTypeParameter> = emptyList(),
        callBlock: (args: Array<out Any?>) -> R,
        modifier: Int= 0
    ): SiCallable<R> = object : SiCallableImpl<R>(){
        override val callBlock: (args: Array<out Any?>) -> R = callBlock
        override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart, modifier)
        override val name: String = nativeCounterpart.qualifiedNativeName
        override val returnType: SiType = returnType
        override val parameters: List<SiParameter> = parameters
        override val typeParameters: List<SiTypeParameter> =
            if(typeParameters.isNotEmpty()) typeParameters
            else ReflexFactoryHelper.getTypeParameter(this, nativeCounterpart.implementation, name)
        override val visibility: SiVisibility = getVisibility(nativeCounterpart.implementation)
        override val isAbstract: Boolean = SiModifier.isAbstract(this)
    }

    internal fun <R> createCallableLazyly(
        nativeCounterpart: SiNativeWrapper,
        host: SiReflex?= null,
//        returnType: SiType= ReflexTemplate.typeAnyNullable,
        parameters: List<SiParameter> = emptyList(),
        typeParameters: List<SiTypeParameter> = emptyList(),
        callBlock: (args: Array<out Any?>) -> R,
        modifier: Int= 0
    ): SiCallable<R> = object : SiCallableImpl<R>(){
        override val callBlock: (args: Array<out Any?>) -> R = callBlock
        override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart, modifier)
        override val name: String = nativeCounterpart.qualifiedNativeName
        override val returnType: SiType by reevaluateLazy {
            val type= getReturnType(nativeCounterpart.implementation)
            it.value= platform != Platform.JS || isTypeFinal(type.descriptor.native!!)
            type
        }
        override val parameters: List<SiParameter> = parameters
        override val typeParameters: List<SiTypeParameter> =
            if(typeParameters.isNotEmpty()) typeParameters
            else ReflexFactoryHelper.getTypeParameter(this, nativeCounterpart.implementation, name)
        override val visibility: SiVisibility = getVisibility(nativeCounterpart.implementation)
        override val isAbstract: Boolean = SiModifier.isAbstract(this)
    }


    fun <R> createFunction(
        nativeCounterpart: SiNativeWrapper,
        host: SiReflex?= null,
        returnType: SiType= ReflexTemplate.typeAnyNullable,
        parameters: List<SiParameter> = emptyList(),
        typeParameters: List<SiTypeParameter> = emptyList(),
        modifier: Int= 0,
        callBlock: (args: Array<out Any?>) -> R
    ): SiFunction<R> {
        val callable= createCallable(
            nativeCounterpart, host, returnType, parameters, typeParameters, callBlock
        )
        return object : SiFunctionImpl<R>(), SiCallable<R> by callable{
            override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart, modifier) //Agar ownernya jadi SiFunction
            override val callBlock: (args: Array<out Any?>) -> R = callBlock
        }
    }

    internal fun <R> createFunctionLazyly(
        nativeCounterpart: SiNativeWrapper,
        host: SiReflex?= null,
//        returnType: SiType= ReflexTemplate.typeAnyNullable,
        parameters: List<SiParameter> = emptyList(),
        typeParameters: List<SiTypeParameter> = emptyList(),
        modifier: Int= 0,
        callBlock: (args: Array<out Any?>) -> R
    ): SiFunction<R> {
        val callable= createCallableLazyly(
            nativeCounterpart, host, parameters, typeParameters, callBlock
        )
        return object : SiFunctionImpl<R>(), SiCallable<R> by callable{
            override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart, modifier) //Agar ownernya jadi SiFunction
            override val callBlock: (args: Array<out Any?>) -> R = callBlock
        }
    }


    fun <T, R> createProperty1(
        nativeCounterpart: SiNativeWrapper,
        host: SiReflex?= null,
        type: SiType= ReflexTemplate.typeAnyNullable,
        modifier: Int= 0
    ): SiProperty1<T, R> = object : SiProperty1Impl<T, R>(){
        override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart, modifier)
        override val name: String = nativeCounterpart.qualifiedNativeName
        override val returnType: SiType = type
        override val visibility: SiVisibility = getVisibility(nativeCounterpart.implementation)
        override val isAbstract: Boolean = SiModifier.isAbstract(this)
    }

    internal fun <T, R> createProperty1Lazyly(
        nativeCounterpart: SiNativeWrapper,
        host: SiReflex?= null,
//        type: SiType= ReflexTemplate.typeAnyNullable,
        modifier: Int= 0
    ): SiProperty1<T, R> = object : SiProperty1Impl<T, R>(){
        override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart, modifier)
        override val name: String = nativeCounterpart.qualifiedNativeName
        override val returnType: SiType by reevaluateLazy {
            val type= getReturnType(nativeCounterpart.implementation)
            it.value= platform != Platform.JS || isTypeFinal(type.descriptor.native!!)
            type
        }
        override val visibility: SiVisibility = getVisibility(nativeCounterpart.implementation)
        override val isAbstract: Boolean = SiModifier.isAbstract(this)
    }


    fun <T, R> createMutableProperty1(
        nativeCounterpart: SiNativeWrapper,
        host: SiReflex?= null,
        type: SiType= ReflexTemplate.typeAnyNullable,
        modifier: Int= 0
    ): SiMutableProperty1<T, R> = object : SiMutableProperty1Impl<T, R>(){
        override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart, modifier)
        override val name: String = nativeCounterpart.qualifiedNativeName
        override val returnType: SiType = type
        override val visibility: SiVisibility = getVisibility(nativeCounterpart.implementation)
        override val isAbstract: Boolean = SiModifier.isAbstract(this)
    }


    internal fun <T, R> createMutableProperty1Lazyly(
        nativeCounterpart: SiNativeWrapper,
        host: SiReflex?= null,
//        type: SiType= ReflexTemplate.typeAnyNullable,
        modifier: Int= 0
    ): SiMutableProperty1<T, R> = object : SiMutableProperty1Impl<T, R>(){
        override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart, modifier)
        override val name: String = nativeCounterpart.qualifiedNativeName
        override val returnType: SiType by reevaluateLazy {
            val type= getReturnType(nativeCounterpart.implementation)
            it.value= platform != Platform.JS || isTypeFinal(type.descriptor.native!!)
            type
        }
        override val visibility: SiVisibility = getVisibility(nativeCounterpart.implementation)
        override val isAbstract: Boolean = SiModifier.isAbstract(this)
    }


    fun <T: Any> createClass(
        nativeCounterpart: SiNativeWrapper,
        host: SiReflex? = null,
        constructors: List<SiFunction<T>> = emptyList(),
        members: Collection<SiCallable<*>> = emptyList(),
        typeParameters: List<SiTypeParameter> = emptyList(),
        modifier: Int= 0
    ): SiClass<T> = object : SiClassImpl<T>() {
        override val descriptor: SiDescriptor = createDescriptor(host, nativeCounterpart, modifier)
        override val qualifiedName: String? = nativeCounterpart.qualifiedNativeName
        override val simpleName: String? = nativeCounterpart.nativeSimpleName //ReflexFactoryHelper.getSimpleName(nativeCounterpart, qualifiedName)
        override var members: Collection<SiCallable<*>> = members
        override var constructors: List<SiFunction<T>> = constructors
        override val typeParameters: List<SiTypeParameter> by lazy {
            if(typeParameters.isNotEmpty()) typeParameters
            else ReflexFactoryHelper.getTypeParameter(this, nativeCounterpart.implementation, qualifiedName)
        }
        override val supertypes: List<SiType> by lazy{
            ReflexFactoryHelper.getSupertypes(this, nativeCounterpart.implementation, qualifiedName)
        }
        override val visibility: SiVisibility = getVisibility(nativeCounterpart.implementation)
        override val isAbstract: Boolean = SiModifier.isAbstract(this)
    }

    internal fun <T, R> createPropertyGetter1(
        property: SiProperty1<T, R>, visibility: SiVisibility = SiVisibility.PUBLIC,
        modifier: Int= 0
    ): SiPropertyGetter1<T, R> = object : SiPropertyGetter1<T, R>(property){
        override val descriptor: SiDescriptor = createDescriptor(property, modifier = modifier)
        override val visibility: SiVisibility = visibility
        override val isAbstract: Boolean = SiModifier.isAbstract(this)
    }
    internal fun <T, R> createPropertySetter1(
        property: SiProperty1<T, R>, visibility: SiVisibility = SiVisibility.PUBLIC,
        modifier: Int= 0
    ): SiPropertySetter1<T, R> = object : SiPropertySetter1<T, R>(property){
        override val descriptor: SiDescriptor = createDescriptor(property, modifier = modifier)
        override val visibility: SiVisibility = visibility
        override val isAbstract: Boolean = SiModifier.isAbstract(this)
    }
}