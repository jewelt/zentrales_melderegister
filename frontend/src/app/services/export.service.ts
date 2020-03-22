import {Injectable} from '@angular/core';
import {TestPatientTestResultDTO} from "../clients/melderegister";

@Injectable({
  providedIn: 'root'
})
export class ExportService {

  constructor() {
  }

  export(data: TestPatientTestResultDTO[]) {
    const csv = 'Fallnummer;Geburtsdatum;Patientennummer;Testdatum;Ergebnisdatum;Testergebnis\n'
      + data.map(datum => {
        return datum.id + ';'
          + datum.patientDTO.birthday + ';'
          + datum.patientDTO.id + ';'
          + datum.testDate + ';'
          + datum.resultDate + ';'
          + datum.testResultDTO.description;
      }).join('\n');
    const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
    const link = document.createElement('a');
    link.setAttribute('href', URL.createObjectURL(blob));
    link.setAttribute('download', 'my_data.csv');
    document.body.appendChild(link); // Required for FF
    link.click();
    document.body.removeChild(link); // Required for FF
  }
}
