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

