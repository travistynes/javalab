package p.config;

import io.crnk.core.boot.CrnkBoot;
import io.crnk.core.queryspec.pagingspec.NumberSizePagingBehavior;
import io.crnk.spring.setup.boot.core.CrnkBootConfigurer;
import io.crnk.ui.UIModule;
import io.crnk.ui.UIModuleConfig;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class CrnkConfig implements CrnkBootConfigurer {

    @Override
    public void configure(CrnkBoot crnkBoot) {
        Objects.requireNonNull(crnkBoot, "crnkBoot cannot be null!");

        /*
         * Enable NumberSizePagingBehavior
         *
         * Number/Size Paging parameters can then look like:
         * GET /tasks?page[number]=1&page[size]=10
         *
         * Refer https://www.crnk.io/releases/stable/documentation/#pagination
         */
        crnkBoot.addModule(NumberSizePagingBehavior.createModule());
    }
}
