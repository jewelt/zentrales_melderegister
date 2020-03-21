import {Component, OnInit} from '@angular/core';
import {TableColumn} from 'simplemattable';
import {TestControllerService, TestResultControllerService} from '../clients/melderegister';
import {TestPatientTestResultDTO} from '../clients/melderegister/model/testPatientTestResultDTO';
import {MatSnackBar} from '@angular/material/snack-bar';
import * as moment from 'moment';

@Component({
  selector: 'app-liste',
  templateUrl: './test-list.component.html',
  styleUrls: ['./test-list.component.scss']
})
export class TestListComponent implements OnInit {

  private dateFormat = 'YYYY-MM-DD HH:mm';
  columns: TableColumn<TestPatientTestResultDTO, any>[] = [];
  data: TestPatientTestResultDTO[] = [];

  constructor(private testControllerService: TestControllerService,
              private testResultControllerService: TestResultControllerService,
              private matSnackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.columns = [
      new TableColumn<TestPatientTestResultDTO, 'patientDTO'>('Geburtsdatum', 'patientDTO')
        .withTransform(patient => patient.birthday),
      new TableColumn<TestPatientTestResultDTO, 'testDate'>('Testdatum', 'testDate')
        .withTransform(testDate => moment(testDate).format(this.dateFormat)),
      new TableColumn<TestPatientTestResultDTO, 'resultDate'>('Ergebnisdatum', 'resultDate')
        .withTransform(resultDate => moment(resultDate).format(this.dateFormat)),
      new TableColumn<TestPatientTestResultDTO, 'testResultDTO'>('Testergebnis', 'testResultDTO')
        .withTransform(testResult => testResult.description),
    ];
    this.testControllerService.getAllTestsWithPatientsUsingGET().subscribe(tests => {
      this.data = tests;
    });
  }

  delete(testToDelete: TestPatientTestResultDTO) {
    this.testControllerService.deleteTestUsingDELETE(testToDelete.id).subscribe(() => {
      this.data = this.data.filter(test => test.id !== testToDelete.id);
      this.matSnackBar.open('Test gelöscht.', 'OK', {
        duration: 3000
      });
    }, error => {
      this.matSnackBar.open('Test konnte nicht gelöscht werden.', 'OK', {
        duration: 3000
      });
    });
  }
}
