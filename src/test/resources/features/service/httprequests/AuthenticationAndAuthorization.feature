#language: es
@FeatureName:AuthenticationAndAuthorization
Característica: Realizar automatizaciones de API tipo REST
  Como tester
  Quiero aprender a probar peticiones http automatizadas
  Para generar valor en mi trabajo

  #Given: Content type, set cookies, add auth, add param, set headers info, etc...
  #When: get, post, put, delete
  #Then: Validate status code, extract response, extract headers cookies & response body...

  Escenario: Autenticacion basica
    Dado que el tester cuenta con el recurso para autenticarse
    Cuando el tester realiza la peticion de autenticacion basica
    Entonces el tester se autentica correctamente

  Escenario: Digest authentication
    Dado que el tester cuenta con el recurso para autenticarse
    Cuando el tester realiza la peticion de Digest authentication
    Entonces el tester se autentica correctamente

  Escenario: Preemptive authentication
    Dado que el tester cuenta con el recurso para autenticarse
    Cuando el tester realiza la peticion de Preemptive authentication
    Entonces el tester se autentica correctamente

  Escenario: Bearer token Authentication
    Dado que el tester cuenta con el recurso para autenticarse con Bearer Token
    Cuando el tester realiza la peticion de Bearer Token authentication
    Entonces el tester se autentica correctamente con Bearer Token

#  Escenario: OAut1 Authentication  --- Se comenta porque requiere autenticaciòn especial que hay que preguntarle a los desarrolladores
#    Dado que el tester cuenta con el recurso para autenticarse con OAut1
#    Cuando el tester realiza la peticion de OAut1 authentication
#    Entonces el tester se autentica correctamente con OAut1
#
#  Escenario: OAut2 Authentication  --- Se comenta porque requiere autenticaciòn especial que hay que preguntarle a los desarrolladores
#    Dado que el tester cuenta con el recurso para autenticarse con OAut2
#    Cuando el tester realiza la peticion de OAut2 authentication
#    Entonces el tester se autentica correctamente con OAut2

  Escenario: API Key Authentication
    Dado que el tester cuenta con el recurso para autenticarse con API Key
    Cuando el tester realiza la peticion de API Key authentication
    Entonces el tester se autentica correctamente con API Key

