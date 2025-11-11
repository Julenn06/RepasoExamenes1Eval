import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AnimalesRemoteService } from '../services/animales.remote';
import { Animal } from '../animal/animal';

@Component({
  selector: 'app-animalito',
  imports: [],
  templateUrl: './animalito.html',
  styleUrl: './animalito.css',
})
export class Animalito {

  elBoss: Animal[] = [];

  constructor(private route: ActivatedRoute, private remote: AnimalesRemoteService ) {}

  id : number = 0;

  ngOnInit() {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.remote.getAnimalById(this.id).subscribe({
      next: (hs) =>
        this.elBoss = [hs]  
    });
  }



}
