package com.example.accesologin.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.NamespaceList
import org.simpleframework.xml.Root

@Root(name = "Envelope")
@NamespaceList(
    Namespace(prefix = "soap", reference = "http://schemas.xmlsoap.org/soap/envelope/"),
    Namespace(prefix = "xsi", reference = "http://www.w3.org/2001/XMLSchema-instance"),
    Namespace(prefix = "xsd", reference = "http://www.w3.org/2001/XMLSchema"),
)
data class SoapEnvelope<T>(
    @field:Element(name = "Body")
    var body: T? = null
)

@Root(name = "accesoLogin")
@Namespace(reference = "http://tempuri.org/")
data class AccesoLoginRequest(
    @field:Element(name = "strMatricula")
    var strMatricula: String? = null,

    @field:Element(name = "strContrasenia")
    var strContrasenia: String? = null,

    @field:Element(name = "tipoUsuario")
    var tipoUsuario: String? = null
)

@Root(name = "accesoLoginResponse")
@Namespace(reference = "http://tempuri.org/")
data class AccesoLoginResponse(
    @field:Element(name = "accesoLoginResult")
    var accesoLoginResult: String? = null
)