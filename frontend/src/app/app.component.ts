import {AfterViewInit, Component} from '@angular/core';
import {LoadingService} from './services/loading.service';
import {delay} from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements AfterViewInit {
  title = 'frontend';
  progressValue = 0;
  mode: 'determinate' | 'indeterminate' = 'determinate';

  constructor(private loadingService: LoadingService,
  ) {
  }

  ngAfterViewInit(): void {
    // delay to avoid expression changed error
    this.loadingService.loadingStatusObservable.pipe(delay(0)).subscribe((loadingStatus) => {
      if (loadingStatus.pending === 0) {
        this.progressValue = 0;
      } else {
        this.mode = loadingStatus.recentlyFinished === 0 ? 'indeterminate' : 'determinate';
        this.progressValue = loadingStatus.recentlyFinished / (loadingStatus.pending + loadingStatus.recentlyFinished) * 100;
      }
    });
  }

}
