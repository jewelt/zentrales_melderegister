import {Component, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FormControl } from "@angular/forms";

@Component({
  selector: 'app-erfassung',
  templateUrl: './erfassung.component.html',
  styleUrls: ['./erfassung.component.scss']
})
export class ErfassungComponent implements OnInit {

  formGroup: FormGroup;
  result: string;
  testResults: string[] = ['Positiv', 'Negativ'];

  constructor(private fb: FormBuilder) {
  }

  ngOnInit() {
    this.formGroup = this.fb.group({
      birthdate: this.fb.control('', Validators.required),
      doctor: this.fb.control('', Validators.required),
      testdate: this.fb.control('', Validators.required),
      testresult: this.fb.control('', Validators.required),
    });
  }

}
