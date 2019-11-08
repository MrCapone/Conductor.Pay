package com.orange_infinity.onlinepay.daggerConfigurations

import com.orange_infinity.onlinepay.data.db.TicketRepository
import com.orange_infinity.onlinepay.data.network.PayInfoService
import com.orange_infinity.onlinepay.ui.presenter.MainActivityPresenter
import com.orange_infinity.onlinepay.useCase.*
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideMainActivityPresenter(
        ticketManager: TicketManager,
        updateController: UpdateController
    ): MainActivityPresenter = MainActivityPresenter(ticketManager = ticketManager, updateController = updateController)

    @Provides
    fun provideTicketManager(ticketRepository: TicketRepository): TicketManager = TicketManager(ticketRepository)

    @Provides
    fun provideUpdateController(programUpdater: ProgramUpdater): UpdateController = UpdateController(programUpdater)

    @Provides
    fun provideServerController(payInfoService: PayInfoService): ServerController = ServerController(payInfoService)

    @Provides
    fun provideTicketRepository(): TicketRepository = TicketRepository()

    @Provides
    fun provideProgramUpdater(): ProgramUpdater = ProgramUpdater()

    @Provides
    fun provideYandexSdkManager(): YandexSdkManager = YandexSdkManager()

    @Provides
    fun provideNfcController(): NfcController = NfcController()

    @Provides
    fun providePayInfoService(): PayInfoService = PayInfoService()
}