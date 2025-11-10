using Repaso01.Models;

namespace Repaso01.Ejercicios;

public static class EjerciciosCount
{
    public static void Ejecutar(List<Libro> libros, List<Empleado> empleados)
    {
        Console.WriteLine("\n═══════════════════════════════════════════════════════");
        Console.WriteLine("EJERCICIOS: COUNT()");
        Console.WriteLine("═══════════════════════════════════════════════════════\n");

        // Ejercicio 1: ¿Cuántos libros hay publicados después del año 2000?
        Console.WriteLine("Ejercicio 1: ¿Cuántos libros publicados después del 2000?");
        int librosRecientes = libros.Count(l => l.AñoPublicacion > 2000);
        Console.WriteLine($"Respuesta: {librosRecientes} libros\n");

        // Ejercicio 2: ¿Cuántos empleados del departamento IT tienen más de 5 años de experiencia?
        Console.WriteLine("Ejercicio 2: ¿Cuántos empleados de IT con más de 5 años exp?");
        int expertosIT = empleados.Count(e => e.Departamento == "IT" && e.AñosExperiencia > 5);
        Console.WriteLine($"Respuesta: {expertosIT} empleados\n");

        // Ejercicio 3: ¿Cuántos libros tienen valoración mayor o igual a 4.5?
        Console.WriteLine("Ejercicio 3: ¿Cuántos libros con valoración >= 4.5?");
        int librosBienValorados = libros.Count(l => l.Valoracion >= 4.5);
        Console.WriteLine($"Respuesta: {librosBienValorados} libros\n");

        // Ejercicio 4: ¿Cuántos empleados inactivos hay en total?
        Console.WriteLine("Ejercicio 4: ¿Cuántos empleados inactivos hay?");
        int empleadosInactivos = empleados.Count(e => !e.Activo);
        Console.WriteLine($"Respuesta: {empleadosInactivos} empleados\n");
    }
}
