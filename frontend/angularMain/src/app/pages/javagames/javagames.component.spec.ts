import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JavagamesComponent } from './javagames.component';

describe('JavagamesComponent', () => {
  let component: JavagamesComponent;
  let fixture: ComponentFixture<JavagamesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JavagamesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(JavagamesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
