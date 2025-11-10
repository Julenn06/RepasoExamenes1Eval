namespace Repaso01.Models;

public class Empleado
{
    public int Id { get; set; }
    public string Nombre { get; set; }
    public string Departamento { get; set; }
    public decimal Salario { get; set; }
    public int AñosExperiencia { get; set; }
    public bool Activo { get; set; }

    public Empleado(int id, string nombre, string departamento, decimal salario, int añosExperiencia, bool activo)
    {
        Id = id;
        Nombre = nombre;
        Departamento = departamento;
        Salario = salario;
        AñosExperiencia = añosExperiencia;
        Activo = activo;
    }

    public override string ToString()
    {
        return $"{Nombre} - {Departamento} - ${Salario} - {AñosExperiencia} años exp - {(Activo ? "ACTIVO" : "INACTIVO")}";
    }
}
