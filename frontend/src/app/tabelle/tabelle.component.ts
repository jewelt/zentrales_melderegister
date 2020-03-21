import {Component, OnInit} from '@angular/core';
import {TableColumn} from 'simplemattable';
import {TestControllerService, TestPatientTestResultDTO} from '../clients/melderegister';
import * as moment from 'moment';

@Component({
  selector: 'app-tabelle',
  templateUrl: './tabelle.component.html',
  styleUrls: ['./tabelle.component.scss']
})
export class TabelleComponent implements OnInit {

  private dateFormat = 'YYYY-MM-DD HH:mm';
  columns: TableColumn<TestPatientTestResultDTO, any>[] = [];
  data: TestPatientTestResultDTO[] = [];

  constructor(private testControllerService: TestControllerService) {
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

}
