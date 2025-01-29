#language: es
@FeatureName:HttpRequests
Característica: Probar requests en Reqres
  Como tester
  Quiero aprender a probar peticiones http automatizadas
  Para generar valor en mi trabajo

  #Given: Content type, set cookies, add auth, add param, set headers info, etc...
  #When: get, post, put, delete
  #Then: Validate status code, extract response, extract headers cookies & response body...

  Esquema del escenario: Obtener usuarios de forma exitosa
    Dado que el tester cuenta con el recurso para hacer la peticion http
    Cuando el tester envia la peticion para obtener los usuarios de la pagina <numero de pagina>
    Entonces se obtiene la lista de usuarios de la pagina <numero de pagina> correctamente

    Ejemplos:
      | numero de pagina |
      | "1"              |
      | "2"              |
      | "3"              |

