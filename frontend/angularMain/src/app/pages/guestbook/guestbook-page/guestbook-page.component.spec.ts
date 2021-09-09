import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GuestbookPageComponent } from './guestbook-page.component';

describe('GuestbookPageComponent', () => {
  let component: GuestbookPageComponent;
  let fixture: ComponentFixture<GuestbookPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GuestbookPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GuestbookPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
