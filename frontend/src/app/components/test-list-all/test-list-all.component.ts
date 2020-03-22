import {Component, OnInit} from '@angular/core';
import {TableColumn} from 'simplemattable';
import {TestControllerService, TestPatientTestResultDTO} from '../../clients/melderegister';
import * as moment from 'moment';
import {ExportService} from '../../services/export.service';

@Component({
  selector: 'app-tabelle',
  templateUrl: './test-list-all.component.html',
  styleUrls: ['./test-list-all.component.scss']
})
export class TestListAllComponent implements OnInit {

  private dateFormat = 'YYYY-MM-DD HH:mm';
  columns: TableColumn<TestPatientTestResultDTO, any>[] = [];
  data: TestPatientTestResultDTO[] = [];

  constructor(private testControllerService: TestControllerService,
              private exportService: ExportService) {
  }

  ngOnInit() {
    this.columns = [
      new TableColumn<TestPatientTestResultDTO, 'id'>('Fallnummer', 'id'),
      new TableColumn<TestPatientTestResultDTO, 'patientDTO'>('Geburtsdatum', 'patientDTO')
        .withTransform(patient => patient.birthday),
      new TableColumn<TestPatientTestResultDTO, 'patientDTO'>('Patientennummer', 'patientDTO')
        .withTransform(patient => patient.id.toString()),
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

  csvExport() {
    this.exportService.export(this.data);
  }

}
