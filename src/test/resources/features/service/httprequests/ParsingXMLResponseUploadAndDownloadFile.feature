#language: es
@FeatureName:ParsingXMLResponse
Característica: : Validación del campo "status" en la API de tareas

#  Escenario: Verificar la respuesta correcta de una peticion desde un valor en el body XML
#    Dado que el tester cuenta con el recurso para obtener informacion XML
#    Cuando el tester hace la peticion para obtener la informacion en formato XML
#    Entonces el tester observa que la respuesta XML es correcta

  Escenario: Verificar la respuesta correcta de una peticion desde un valor en el body XML con XMLObject
    Dado que el tester cuenta con el recurso para obtener informacion XML
    Cuando el tester hace la peticion para obtener la informacion en formato XML
    Entonces el tester observa que la respuesta XML es correcta al usar XMLObject
