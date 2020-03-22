import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {
  CityControllerService,
  CityDTO,
  PatientControllerService,
  PatientDTO,
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
  patients: PatientDTO[] = [];
  isNew = true;

  constructor(private fb: FormBuilder,
              private patientControllerService: PatientControllerService,
              private testControllerService: TestControllerService,
              private matSnackBar: MatSnackBar,
              private cityControllerService: CityControllerService,
              private testResultControllerService: TestResultControllerService) {
  }

  ngOnInit() {
    this.formGroup = this.fb.group({
      birthdate: this.fb.control(''),
      doctor: this.fb.control('', Validators.required),
      testdate: this.fb.control(moment(), Validators.required),
      testtime: this.fb.control(moment().hour() + ':' + moment().minute()),
      resultdate: this.fb.control(moment(), Validators.required),
      resulttime: this.fb.control(moment().hour() + ':' + moment().minute()),
      testresult: this.fb.control('', Validators.required),
      city: this.fb.control('', Validators.required),
      patientid: this.fb.control(null),
    });

    this.patientControllerService.getAllPatientsUsingGET().subscribe(patients => {
      this.patients = patients.sort((a, b) => {
        if (a.id > b.id) {
          return -1;
        } else if (a.id < b.id) {
          return 1;
        } else {
          return 0;
        }
      });
    });

    this.testResultControllerService.getAllTestResultsUsingGET().subscribe((testResults) => {
      this.testResults = testResults;
    });
    this.cityControllerService.getAllCitiesByUserUsingGET().subscribe(cities => {
      this.cities = cities;
      this.formGroup.get('city').patchValue(cities[0]);
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
    const patientid = this.formGroup.get('patientid').value as string;

    if (this.isNew && !birthday) {
      return;
    }
    if (!this.isNew && !patientid) {
      return;
    }

    testdate.hour(+(testtime.split(':')[0]));
    testdate.minute(+(testtime.split(':')[1]));

    resultdate.hour(+(resulttime.split(':')[0]));
    resultdate.minute(+(resulttime.split(':')[1]));

    if (this.isNew) { // new
      this.patientControllerService.createPatientUsingPOST({
        birthday: birthday.toISOString(true).substring(0, 10),
        cityId: city.id
      }).subscribe(patient => {
        this.patients.unshift(patient);
        this.createTest(patient, resultdate, testdate, testresult);
      }, error => {
        this.matSnackBar.open('Test konnte nicht angelegt werden', 'OK', {
          duration: 3000
        });
      });
    } else { // existing
      const patient = this.patients.find(patientFind => patientFind.id === +patientid);
      if (!patient) {
        return;
      }
      this.createTest(patient, resultdate, testdate, testresult);
    }

  }

  private createTest(patient: PatientDTO, resultdate: moment.Moment, testdate: moment.Moment, testresult: TestResultDTO) {
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
  }


}
