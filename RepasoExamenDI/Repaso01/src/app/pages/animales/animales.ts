import {
  ChangeDetectorRef,
  Component
} from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AnimalesRemoteService } from '../services/animales.remote';
import { Animal } from '../animal/animal';

/**
 * Componente para gestionar animales
 * Permite crear, leer, actualizar y eliminar animales
 */
@Component({
  selector: 'app-animales',
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './animales.html',
  styleUrl: './animales.css',
})
export class Animales {

  // Estados de la UI
  crearAnimalInput = true;
  editarAnimalInput = true;
  
  // Datos del formulario de creación
  nombre: string = "";
  tipo: string = "";
  
  // Datos del formulario de edición
  nombreNuevo: string = "";
  tipoNuevo: string = "";
  
  // ID para búsqueda, edición y eliminación
  idAnimal: number = 0;
  
  // Lista de animales
  todosLosAnimales: Animal[] = [];

  constructor(private cdr: ChangeDetectorRef, private remote : AnimalesRemoteService){}

  anadirAnimal() {
    // Validar que los campos no estén vacíos
    if (!this.nombre.trim()) {
      console.error('El nombre es obligatorio');
      return;
    }

    this.remote.createAnimal(this.nombre.trim(), this.tipo.trim()).subscribe({
      next: (animalCreado) => {
        console.log('Animal creado exitosamente:', animalCreado);
        this.verAnimales(); // Actualizar lista
        this.limpiarDatos();
      },
      error: (err) => console.error('Error creando animal', err)
    });
  }

  verAnimales() {
    this.remote.getAllAnimales().subscribe({
      next: (hs) => {
        this.todosLosAnimales = hs;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error cargando Animales', err);
      }
    });
  }

  verAnimalPorId() {
    // Validar que el ID sea válido
    if (!this.idAnimal || this.idAnimal <= 0) {
      console.error('ID inválido');
      return;
    }

    this.remote.getAnimalById(this.idAnimal).subscribe({
      next: (animal) => {
        this.todosLosAnimales = [animal];
        console.log('Animal encontrado:', animal);
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error cargando Animal por ID', err);
        this.todosLosAnimales = []; // Limpiar lista si no se encuentra
        this.cdr.markForCheck();
      }
    });
  }



  nuevosDatos() {
    // Validar que el ID sea válido
    if (!this.idAnimal || this.idAnimal <= 0) {
      console.error('ID inválido para editar');
      return;
    }

    // Validar que al menos un campo tenga datos
    if (!this.nombreNuevo.trim() && !this.tipoNuevo.trim()) {
      console.error('Debe proporcionar al menos un campo para actualizar');
      return;
    }

    // Crear objeto con solo los campos que tienen datos
    const changes: Partial<Animal> = {};
    if (this.nombreNuevo.trim()) changes.nombre = this.nombreNuevo.trim();
    if (this.tipoNuevo.trim()) changes.tipo = this.tipoNuevo.trim();

    this.remote.updateAnimal(this.idAnimal, changes).subscribe({
      next: (animalActualizado) => {
        console.log('Animal actualizado:', animalActualizado);
        this.verAnimales(); // Actualizar lista
        this.limpiarDatosEdicion();
      },
      error: (err) => console.error('Error actualizando animal', err)
    });
  }

  eliminarAnimal() {
    // Validar que el ID sea válido
    if (!this.idAnimal || this.idAnimal <= 0) {
      console.error('ID inválido para eliminar');
      return;
    }

    // Confirmación antes de eliminar
    if (!confirm(`¿Está seguro de eliminar el animal con ID ${this.idAnimal}?`)) {
      return;
    }

    this.remote.deleteAnimal(this.idAnimal).subscribe({
      next: () => {
        console.log(`Animal con ID ${this.idAnimal} eliminado`);
        this.verAnimales(); // Actualizar lista
        this.idAnimal = 0; // Limpiar ID
      },
      error: (err) => console.error('Error eliminando animal', err)
    });
  }

  limpiarDatos() {
    this.crearAnimalInput = true;
    this.nombre = "";
    this.tipo = "";
  }

  limpiarDatosEdicion() {
    this.editarAnimalInput = true;
    this.nombreNuevo = "";
    this.tipoNuevo = "";
  }
}
