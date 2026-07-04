package defpackage;

import java.util.concurrent.Executors;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class u7 extends t20 {
    public static volatile u7 b;
    public final Object a;

    public u7(int i) {
        switch (i) {
            case 1:
                this.a = new Object();
                Executors.newFixedThreadPool(4, new em());
                return;
            default:
                this.a = new u7(1);
                return;
        }
    }
}
