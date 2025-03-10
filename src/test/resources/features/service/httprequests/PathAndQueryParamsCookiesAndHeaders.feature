#language: es
@FeatureName:PathAndQueryParamsCookiesAndHeaders
Característica: Probar requests en Reqres
  Como tester
  Quiero aprender a probar peticiones http automatizadas
  Para generar valor en mi trabajo

  #Given: Content type, set cookies, add auth, add param, set headers info, etc...
  #When: get, post, put, delete
  #Then: Validate status code, extract response, extract headers cookies & response body...

  @CallApiRegresIn
  Esquema del escenario: Obtener informacion de usuarios empleando PATH y QUERY parameters
    Dado que el tester cuenta con el recurso para hacer la peticion http con parametros
    Cuando el tester envia la peticion para obtener los usuarios con path <path> con pagina <page> y con id <id>
    Entonces se obtiene el usuario de la pagina <page> con id <id> correctamente

    Ejemplos:
      | path       | page | id |
      | /api/users | 2    | 5  |
      | /api/users | 1    | 3  |

  Escenario: Obtener cookies de un sitio web
    Dado que el tester tiene el recurso para realizar la peticion http
    Cuando el tester envia la peticion para obtener las cookies del sitio
    Entonces el tester obtiene las cookies correctamente

  Escenario: Validar Headers de la respuesta de una peticion HTTP
    Dado que el tester tiene el recurso para realizar la peticion http
    Cuando el tester envia la peticion para obtener los headers del sitio
    Entonces el tester valida que los headers sean correctos

