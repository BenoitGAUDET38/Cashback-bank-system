package fr.teama.cashbackservice.interfaces.proxy;

import java.util.List;

public interface ICarrefourProxy {

    List<Long> getCashbackTransactionsAbortedID(String apiBaseUrlHostAndPort);
}
