import {
  Component
} from '@angular/core';
import {
  CommonModule
} from '@angular/common';
import {
  JsonService
} from '../../services/json-service';
import {
  Observable
} from 'rxjs';

@Component({
  selector: 'app-prueba',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './prueba.html',
  styleUrls: ['./prueba.css']
})
export class Prueba {
  data$ ? : Observable < any >

    constructor(private jsonService: JsonService) {}

  leerTodo(): void {
    this.data$ = this.jsonService.getDb();
  }

  leerPorId(): void {
    let number = 0;
    const inputElement = document.getElementById('number') as HTMLInputElement;
    number = parseInt(inputElement?.value ?? '0', 10);
    this.data$ = this.jsonService.getDbById(number);
  }

}
