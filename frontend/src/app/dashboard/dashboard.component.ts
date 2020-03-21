import {Component, OnInit} from '@angular/core';
import {map} from 'rxjs/operators';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {StatisticsControllerService} from '../clients/melderegister';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  // based on the screen size, switch from standard to one column per row
  cards = this.breakpointObserver.observe(Breakpoints.Handset).pipe(
    map(({matches}) => {
      if (matches) {
        return [
          {title: 'Anzahl nach Bundesland', cols: 2, rows: 1, type: DiagramType.ANZAHL_BUNDESLAND},
          {title: 'Bundesland', cols: 2, rows: 1}
        ];
      }

      return [
        {title: 'Anzahl nach Bundesland', cols: 1, rows: 1, type: DiagramType.ANZAHL_BUNDESLAND},
        {title: 'Bundesland', cols: 1, rows: 1}
      ];
    })
  );

  // options
  showXAxis = true;
  showYAxis = true;
  gradient = false;
  showLegend = true;
  showXAxisLabel = true;
  yAxisLabel = 'Country';
  showYAxisLabel = true;
  xAxisLabel = 'Normalized Population';
  diagramType = DiagramType;

  // data goes here
  public infectedByState = [];

  constructor(private breakpointObserver: BreakpointObserver,
              private statisticsControllerService: StatisticsControllerService) {
  }


  onSelect($event: any) {
    console.log($event);
  }

  ngOnInit(): void {

    this.statisticsControllerService.getCountByStateNowUsingGET().subscribe((data) => {
      this.infectedByState = data;
    });


  }
}

enum DiagramType {
  ANZAHL_BUNDESLAND = 1
}
