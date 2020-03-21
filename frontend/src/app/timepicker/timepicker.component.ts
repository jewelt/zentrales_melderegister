import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl} from '@angular/forms';
import * as moment from 'moment';

@Component({
  selector: 'app-timepicker',
  templateUrl: './timepicker.component.html',
  styleUrls: ['./timepicker.component.scss']
})
export class TimepickerComponent implements OnInit {

  @Input() control: FormControl;

  formControlHour: FormControl;
  formControlMinute: FormControl;

  @ViewChild('minuteInput', {static: true})
  minuteInput: ElementRef;

  constructor(private fb: FormBuilder) {
  }

  ngOnInit() {
    this.formControlHour = this.fb.control(moment().hour());
    this.formControlMinute = this.fb.control(moment().minute());

    this.formControlHour.valueChanges.subscribe((newValue) => {
      if (newValue.length === 2) {
        this.minuteInput.nativeElement.focus();
        this.minuteInput.nativeElement.select();
      }
      this.updateTime();
    });
    this.formControlMinute.valueChanges.subscribe(() => {
      this.updateTime();
    });
  }

  updateTime() {
    const hour = this.formControlHour.value;
    const minute = this.formControlMinute.value;
    this.control.setValue(`${hour}:${minute}`);
  }

}
