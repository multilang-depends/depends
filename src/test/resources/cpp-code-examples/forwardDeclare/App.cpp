#include "App.h"
#include "Mutex.h"

void App::foo(){
    Mutex mutex;
    mutex.p();
}

void App::bar(){
    this->mutex->p();
}


void App::bar2(Mutex* mutex){
    mutex->p();
}

