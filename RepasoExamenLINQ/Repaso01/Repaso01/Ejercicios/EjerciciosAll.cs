using Repaso01.Models;

namespace Repaso01.Ejercicios;

public static class EjerciciosAll
{
    public static void Ejecutar(List<Estudiante> estudiantes, List<Empleado> empleados)
    {
        Console.WriteLine("\n═══════════════════════════════════════════════════════");
        Console.WriteLine("EJERCICIOS: ALL()");
        Console.WriteLine("═══════════════════════════════════════════════════════\n");

        // Ejercicio 1: ¿Todos los estudiantes son mayores de edad?
        Console.WriteLine("Ejercicio 1: ¿Todos los estudiantes son mayores de edad (>= 18)?");
        bool todosMayores = estudiantes.All(e => e.Edad >= 18);
        Console.WriteLine($"Respuesta: {(todosMayores ? "Si" : "No")}\n");

        // Ejercicio 2: ¿Todos los empleados activos tienen salario mayor a 1000?
        Console.WriteLine("Ejercicio 2: ¿Todos los empleados activos ganan más de $1000?");
        bool todosSalarioMinimo = empleados.Where(e => e.Activo).All(e => e.Salario > 1000);
        Console.WriteLine($"Respuesta: {(todosSalarioMinimo ? "Si" : "No")}\n");

        // Ejercicio 3: ¿Todos los estudiantes aprobados tienen nota >= 5?
        Console.WriteLine("Ejercicio 3: ¿Todos los estudiantes aprobados tienen nota >= 5?");
        bool todosAprobadosNota5 = estudiantes.Where(e => e.Aprobado).All(e => e.NotaMedia >= 5);
        Console.WriteLine($"Respuesta: {(todosAprobadosNota5 ? "Si" : "No")}\n");

        // Ejercicio 4: ¿Todos los empleados de IT tienen más de 5 años de experiencia?
        Console.WriteLine("Ejercicio 4: ¿Todos los de IT tienen > 5 años experiencia?");
        bool todosITExperimentados = empleados.Where(e => e.Departamento == "IT").All(e => e.AñosExperiencia > 5);
        Console.WriteLine($"Respuesta: {(todosITExperimentados ? "Si" : "No")}\n");
    }
}
