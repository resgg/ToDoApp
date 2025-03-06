import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SkyBackgroundComponent } from './sky-background.component';

describe('SkyBackgroundComponent', () => {
  let component: SkyBackgroundComponent;
  let fixture: ComponentFixture<SkyBackgroundComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SkyBackgroundComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SkyBackgroundComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
