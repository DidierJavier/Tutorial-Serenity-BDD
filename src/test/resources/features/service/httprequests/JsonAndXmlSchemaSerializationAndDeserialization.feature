#language: es
@FeatureName:JsonAndXmlSchemaSerializationAndDeserialization
Característica: Validacion con Json y XML schema, serializacion y deserializacion
  Como tester
  Quiero aprender a probar peticiones http automatizadas
  Para generar valor en mi trabajo

  #Given: Content type, set cookies, add auth, add param, set headers info, etc...
  #When: get, post, put, delete
  #Then: Validate status code, extract response, extract headers cookies & response body...

  Escenario: Validacion con Json schema
    Dado que el tester cuenta con el recurso para hacer la peticion http y el archivo json schema
    Cuando el tester envia la peticion para comparar la respuesta con Json schema
    Entonces al comparar la respuesta de la peticion con el Json schema es correcta

  Escenario: Validacion de respuesta en XSL con XSD schema
    Dado que el tester cuenta con el recurso para hacer la peticion http y el archivo xsd schema
    Cuando el tester envia la peticion para comparar la respuesta con xsd schema
    Entonces al comparar la respuesta XSL de la peticion con el XSD schema es correcta
