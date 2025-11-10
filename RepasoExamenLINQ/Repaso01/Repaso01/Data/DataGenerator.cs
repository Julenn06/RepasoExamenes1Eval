using Repaso01.Models;

namespace Repaso01.Data;

public static class DataGenerator
{
    public static List<Estudiante> ObtenerEstudiantes()
    {
        return new List<Estudiante>
        {
            new Estudiante(1, "Ana García", 20, 8.5, true, "DAM"),
            new Estudiante(2, "Carlos López", 19, 6.8, true, "DAM"),
            new Estudiante(3, "María Pérez", 22, 9.2, true, "ASIR"),
            new Estudiante(4, "Juan Martín", 17, 4.5, false, "DAM"),
            new Estudiante(5, "Laura Sánchez", 21, 7.3, true, "ASIR"),
            new Estudiante(6, "Pedro Ruiz", 20, 5.9, false, "DAM"),
            new Estudiante(7, "Sofía Torres", 23, 9.8, true, "DAM"),
            new Estudiante(8, "Diego Ramírez", 18, 7.8, true, "ASIR")
        };
    }

    public static List<Producto> ObtenerProductos()
    {
        return new List<Producto>
        {
            new Producto(1, "Laptop", 899.99m, 5, "Electrónica", true),
            new Producto(2, "Mouse", 15.50m, 50, "Electrónica", true),
            new Producto(3, "Teclado", 45.00m, 30, "Electrónica", false),
            new Producto(4, "Monitor", 250.00m, 0, "Electrónica", false),
            new Producto(5, "Silla Gamer", 199.99m, 8, "Muebles", true),
            new Producto(6, "Escritorio", 150.00m, 3, "Muebles", false),
            new Producto(7, "Auriculares", 35.99m, 20, "Electrónica", true),
            new Producto(8, "Webcam", 75.00m, 12, "Electrónica", false)
        };
    }

    public static List<Empleado> ObtenerEmpleados()
    {
        return new List<Empleado>
        {
            new Empleado(1, "Roberto Silva", "IT", 3500.00m, 8, true),
            new Empleado(2, "Carmen Vega", "IT", 3200.00m, 6, true),
            new Empleado(3, "Luis Morales", "RRHH", 2800.00m, 4, true),
            new Empleado(4, "Elena Castro", "IT", 4000.00m, 10, true),
            new Empleado(5, "Miguel Ángel", "Ventas", 2500.00m, 3, true),
            new Empleado(6, "Patricia Díaz", "RRHH", 2600.00m, 2, false),
            new Empleado(7, "Francisco Ortiz", "IT", 3800.00m, 7, true),
            new Empleado(8, "Isabel Romero", "Ventas", 2700.00m, 5, true)
        };
    }

    public static List<Libro> ObtenerLibros()
    {
        return new List<Libro>
        {
            new Libro(1, "El Quijote", "Cervantes", 863, 1605, 4.8),
            new Libro(2, "Cien Años de Soledad", "García Márquez", 471, 1967, 4.9),
            new Libro(3, "1984", "George Orwell", 328, 1949, 4.7),
            new Libro(4, "Clean Code", "Robert C. Martin", 464, 2008, 4.6),
            new Libro(5, "El Principito", "Saint-Exupéry", 96, 1943, 4.9),
            new Libro(6, "Harry Potter", "J.K. Rowling", 309, 1997, 4.8),
            new Libro(7, "El Código Da Vinci", "Dan Brown", 656, 2003, 4.2),
            new Libro(8, "Cracking the Coding Interview", "Gayle McDowell", 687, 2015, 4.5)
        };
    }
}
