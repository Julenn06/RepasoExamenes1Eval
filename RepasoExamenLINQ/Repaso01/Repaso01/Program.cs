using Repaso01.Data;
using Repaso01.Ejercicios;

class Program
{
    static void Main(string[] args)
    {
        Console.WriteLine("EJERCICIOS LINQ - PRACTICA PARA EXAMEN");
        Console.WriteLine("=======================================\n");

        var estudiantes = DataGenerator.ObtenerEstudiantes();
        var productos = DataGenerator.ObtenerProductos();
        var empleados = DataGenerator.ObtenerEmpleados();
        var libros = DataGenerator.ObtenerLibros();

        EjerciciosAny.Ejecutar(estudiantes, productos);
        EjerciciosAll.Ejecutar(estudiantes, empleados);
        EjerciciosWhere.Ejecutar(estudiantes, productos);
        EjerciciosCount.Ejecutar(libros, empleados);
        EjerciciosContains.Ejecutar(estudiantes, productos);
        EjerciciosFirstOrDefault.Ejecutar(estudiantes, libros);
        EjerciciosOrderBy.Ejecutar(productos, empleados);
        EjerciciosMaxMin.Ejecutar(estudiantes, libros);
        EjerciciosAverage.Ejecutar(estudiantes, empleados);
        EjerciciosSum.Ejecutar(productos, empleados, estudiantes);
        EjerciciosSelect.Ejecutar(estudiantes, productos);
        EjerciciosCombinados.Ejecutar(estudiantes, productos);

        Console.WriteLine("\nEJERCICIOS COMPLETADOS");
    }
}
