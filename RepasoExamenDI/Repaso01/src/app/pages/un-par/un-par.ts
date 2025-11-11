import { ChangeDetectionStrategy, ChangeDetectorRef, Component } from '@angular/core';
import { Animal } from '../animal/animal';
import { AnimalesRemoteService } from '../services/animales.remote';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-un-par',
  imports: [RouterLink],
  templateUrl: './un-par.html',
  styleUrl: './un-par.css',
})
export class UnPar {

  animalesTop: Animal[] = [];

  constructor(private remote: AnimalesRemoteService, private cdr: ChangeDetectorRef, private router : Router) {};
  ngOnInit(): void {
    this.remote.getFeaturedAnimales(4).subscribe({
      next: (ah) => {
        this.animalesTop = ah;
        this.cdr.markForCheck();
      },
      error: (err) => console.error("no se han podido cargar a los Bros", err)
    });
  }
}
