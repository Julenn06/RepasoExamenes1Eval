using Repaso01.Models;

namespace Repaso01.Ejercicios;

public static class EjerciciosAverage
{
    public static void Ejecutar(List<Estudiante> estudiantes, List<Empleado> empleados)
    {
        Console.WriteLine("\n═══════════════════════════════════════════════════════");
        Console.WriteLine("EJERCICIOS: AVERAGE()");
        Console.WriteLine("═══════════════════════════════════════════════════════\n");

        // Ejercicio 1: Promedio de notas de estudiantes aprobados
        Console.WriteLine("Ejercicio 1: Promedio de notas de estudiantes aprobados:");
        double promedioAprobados = estudiantes.Where(e => e.Aprobado).Average(e => e.NotaMedia);
        Console.WriteLine($"  Promedio: {promedioAprobados:F2}\n");

        // Ejercicio 2: Salario promedio por departamento IT
        Console.WriteLine("Ejercicio 2: Salario promedio del departamento IT:");
        decimal promedioSalarioIT = empleados.Where(e => e.Departamento == "IT").Average(e => e.Salario);
        Console.WriteLine($"  Promedio: ${promedioSalarioIT:F2}\n");

        // Ejercicio 3: Edad promedio de estudiantes de DAM
        Console.WriteLine("Ejercicio 3: Edad promedio de estudiantes de DAM:");
        double edadPromedioDAM = estudiantes.Where(e => e.Carrera == "DAM").Average(e => e.Edad);
        Console.WriteLine($"  Promedio: {edadPromedioDAM:F2} años\n");

        // Ejercicio 4: Promedio de años de experiencia de empleados activos
        Console.WriteLine("Ejercicio 4: Promedio experiencia de empleados activos:");
        double promedioExpActivos = empleados.Where(e => e.Activo).Average(e => e.AñosExperiencia);
        Console.WriteLine($"  Promedio: {promedioExpActivos:F2} años\n");
    }
}
