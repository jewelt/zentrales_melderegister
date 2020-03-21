import { NgModule, ModuleWithProviders, SkipSelf, Optional } from '@angular/core';
import { Configuration } from './configuration';
import { HttpClient } from '@angular/common/http';


import { CityControllerService } from './api/cityController.service';
import { CountryControllerService } from './api/countryController.service';
import { DoctorControllerService } from './api/doctorController.service';
import { PatientControllerService } from './api/patientController.service';
import { StateControllerService } from './api/stateController.service';
import { TestControllerService } from './api/testController.service';
import { TestResultControllerService } from './api/testResultController.service';
import { UserAccountApiControllerService } from './api/userAccountApiController.service';
import { UserJwtApiControllerService } from './api/userJwtApiController.service';

@NgModule({
  imports:      [],
  declarations: [],
  exports:      [],
  providers: [
    CityControllerService,
    CountryControllerService,
    DoctorControllerService,
    PatientControllerService,
    StateControllerService,
    TestControllerService,
    TestResultControllerService,
    UserAccountApiControllerService,
    UserJwtApiControllerService ]
})
export class ApiModule {
    public static forRoot(configurationFactory: () => Configuration): ModuleWithProviders {
        return {
            ngModule: ApiModule,
            providers: [ { provide: Configuration, useFactory: configurationFactory } ]
        };
    }

    constructor( @Optional() @SkipSelf() parentModule: ApiModule,
                 @Optional() http: HttpClient) {
        if (parentModule) {
            throw new Error('ApiModule is already loaded. Import in your base AppModule only.');
        }
        if (!http) {
            throw new Error('You need to import the HttpClientModule in your AppModule! \n' +
            'See also https://github.com/angular/angular/issues/20575');
        }
    }
}
