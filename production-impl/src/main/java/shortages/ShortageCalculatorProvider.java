package shortages;

import entities.DemandEntity;
import entities.ProductionEntity;
import external.CurrentStock;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class ShortageCalculatorProvider {
    private LocalDate today;
    private int daysAhead;
    private CurrentStock stock;
    private List<ProductionEntity> productions;
    private List<DemandEntity> demands;

    public ShortageCalculatorProvider(LocalDate today, int daysAhead, CurrentStock stock, List<ProductionEntity> productions, List<DemandEntity> demands) {
        this.today = today;
        this.daysAhead = daysAhead;
        this.stock = stock;
        this.productions = productions;
        this.demands = demands;
    }

    public ShortageCalculator get(String productRefNo) {
        List<LocalDate> dates = Stream.iterate(today, date -> date.plusDays(1))
                .limit(daysAhead)
                .collect(toList());

        Productions outputs = new Productions(productions);
        Demands demandsPerDay = new Demands(demands);

        return new ShortageCalculator(productRefNo, stock, dates, outputs, demandsPerDay);
    }
}
