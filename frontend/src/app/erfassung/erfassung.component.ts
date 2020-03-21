import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {
  CityControllerService,
  CityDTO,
  PatientControllerService,
  TestControllerService,
  TestResultControllerService,
  TestResultDTO
} from '../clients/melderegister';
import {MatSnackBar} from '@angular/material/snack-bar';
import * as moment from 'moment';

@Component({
  selector: 'app-erfassung',
  templateUrl: './erfassung.component.html',
  styleUrls: ['./erfassung.component.scss']
})
export class ErfassungComponent implements OnInit {

  formGroup: FormGroup;
  result: string;
  testResults: TestResultDTO[] = [];
  cities: CityDTO[] = [];

  constructor(private fb: FormBuilder,
              private patientControllerService: PatientControllerService,
              private testControllerService: TestControllerService,
              private matSnackBar: MatSnackBar,
              private cityControllerService: CityControllerService,
              private testResultControllerService: TestResultControllerService) {
  }

  ngOnInit() {
    this.testResultControllerService.getAllTestResultsUsingGET().subscribe((testResults) => {
      this.testResults = testResults;
    });
    this.cityControllerService.getAllCitiesByUserUsingGET().subscribe(cities => {
      this.cities = cities;
    });


    this.formGroup = this.fb.group({
      birthdate: this.fb.control('', Validators.required),
      doctor: this.fb.control('', Validators.required),
      testdate: this.fb.control(moment(), Validators.required),
      testtime: this.fb.control(moment().hour() + ':' + moment().minute()),
      resultdate: this.fb.control(moment(), Validators.required),
      resulttime: this.fb.control(moment().hour() + ':' + moment().minute()),
      testresult: this.fb.control('', Validators.required),
      city: this.fb.control('', Validators.required),
    });
  }

  onSave() {

    if (!this.formGroup.valid) {
      return;
    }

    const birthday = this.formGroup.get('birthdate').value as moment.Moment;
    const doctor = this.formGroup.get('doctor').value;
    const testdate = this.formGroup.get('testdate').value as moment.Moment;
    const testtime = this.formGroup.get('testtime').value as string;
    const resultdate = this.formGroup.get('resultdate').value as moment.Moment;
    const resulttime = this.formGroup.get('resulttime').value as string;
    const testresult = this.formGroup.get('testresult').value as TestResultDTO;
    const city = this.formGroup.get('city').value as CityDTO;

    testdate.hour(+(testtime.split(':')[0]));
    testdate.minute(+(testtime.split(':')[1]));

    resultdate.hour(+(resulttime.split(':')[0]));
    resultdate.minute(+(resulttime.split(':')[1]));

    console.log(birthday.toISOString(true));
    console.log(doctor);
    console.log(testdate.toISOString(true));
    console.log(resultdate.toISOString(true));
    console.log(testresult);

    this.patientControllerService.createPatientUsingPOST({
      birthday: birthday.toISOString(true).substring(0, 10),
      cityId: city.id
    }).subscribe(patient => {
      this.testControllerService.createTestUsingPOST({
        entryDate: moment().toISOString(true),
        patientId: patient.id,
        resultDate: resultdate.toISOString(true),
        testDate: testdate.toISOString(true),
        testResultId: testresult.id
      }).subscribe((test) => {
        this.matSnackBar.open('Test ' + test.id + ' angelegt', 'OK', {
          duration: 3000
        });
      }, error => {
        this.matSnackBar.open('Test konnte nicht angelegt werden', 'OK', {
          duration: 3000
        });
      });
    }, error => {
      this.matSnackBar.open('Test konnte nicht angelegt werden', 'OK', {
        duration: 3000
      });
    });

  }

}
