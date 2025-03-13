#language: es
@FeatureName:ParsingResponseBody
Característica: : Validación del campo "status" en la API de tareas

  Escenario: Verificar la respuesta correcta de una peticion desde un valor en el body
    Dado que el tester cuenta con el recurso para encontrar un Pokemon en estado salvaje
    Cuando el tester hace la peticion para obtener el Pokemon en estado salvaje
    Entonces el tester observa que el valor del segundo nombre del Pokemon en estado salvaje coincide con el valor esperado

  Escenario: Verificar la respuesta a una peticion con Response de rest-azure
    Dado que el tester cuenta con el recurso para encontrar un Pokemon en estado salvaje
    Cuando el tester hace la peticion para obtener el Pokemon en estado salvaje
    Entonces el tester observa que los valores esperados son iguales a los del Response de rest-azure