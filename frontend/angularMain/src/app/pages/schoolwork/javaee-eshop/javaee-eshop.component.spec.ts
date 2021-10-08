import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JavaeeEshopComponent } from './javaee-eshop.component';

describe('JavaeeEshopComponent', () => {
  let component: JavaeeEshopComponent;
  let fixture: ComponentFixture<JavaeeEshopComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JavaeeEshopComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(JavaeeEshopComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
