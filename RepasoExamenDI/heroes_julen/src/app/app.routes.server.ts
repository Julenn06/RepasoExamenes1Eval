import {
  RenderMode,
  ServerRoute
} from '@angular/ssr';

export const serverRoutes: ServerRoute[] = [{
    path: '',
    renderMode: RenderMode.Prerender,
  },
  {
    path: 'heroes',
    renderMode: RenderMode.Prerender,
  },
  {
    path: 'hero/:id',
    renderMode: RenderMode.Server,
  },
];
