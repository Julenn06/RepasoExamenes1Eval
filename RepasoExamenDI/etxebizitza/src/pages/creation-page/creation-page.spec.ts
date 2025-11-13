import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreationPage } from './creation-page';

describe('CreationPage', () => {
  let component: CreationPage;
  let fixture: ComponentFixture<CreationPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreationPage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreationPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
