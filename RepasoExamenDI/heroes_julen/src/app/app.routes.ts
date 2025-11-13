import {
  Routes
} from '@angular/router';
import {
  HomePage
} from './pages/homepage/home-page';
import {
  HeroesPage
} from './pages/heroespage/heroes-page';
import {
  HeroePage
} from './pages/heroepage/heroe-page';

export const routes: Routes = [{
    path: '',
    component: HomePage
  },
  {
    path: 'heroes',
    component: HeroesPage
  },
  {
    path: 'hero/:id',
    component: HeroePage
  },
];
