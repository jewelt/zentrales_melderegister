import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-erfassung',
  templateUrl: './erfassung.component.html',
  styleUrls: ['./erfassung.component.scss']
})
export class ErfassungComponent implements OnInit {

  formGroup: FormGroup;
  states: string[] = ['Niedersachsen'];
  countries: string[] = ['Landkreis Cloppenburg'];

  constructor(private fb: FormBuilder) {
  }

  ngOnInit() {
    this.formGroup = this.fb.group({
      city: this.fb.control('', Validators.required),
      state: this.fb.control('', Validators.required),
      country: this.fb.control('', Validators.required),
    });
  }

}
