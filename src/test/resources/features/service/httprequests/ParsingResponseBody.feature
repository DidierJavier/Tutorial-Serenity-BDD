#language: es
@FeatureName:ParsingResponseBody
Característica: : Validación del campo "status" en la API de tareas

  Escenario: Verificar el valor del segundo nombre de un Pokemon en estado salvaje
    Dado que el tester cuenta con el recurso para encontrar un Pokemon en estado salvaje
    Cuando el tester hace la peticion para obtener el Pokemon en estado salvaje
    Y el tester valida el segundo nombre de un Pokemon en estado Salvaje
    Entonces el tester valida que el valor del segundo nombre del Pokemon en estado salvaje coincide con el valor esperado