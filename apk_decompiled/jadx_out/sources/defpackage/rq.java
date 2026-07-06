package defpackage;

import java.util.concurrent.ThreadPoolExecutor;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class rq extends o4 {
    public final /* synthetic */ o4 k;
    public final /* synthetic */ ThreadPoolExecutor l;

    public rq(o4 o4Var, ThreadPoolExecutor threadPoolExecutor) {
        this.k = o4Var;
        this.l = threadPoolExecutor;
    }

    @Override // defpackage.o4
    public final void R(Throwable th) {
        ThreadPoolExecutor threadPoolExecutor = this.l;
        try {
            this.k.R(th);
        } finally {
            threadPoolExecutor.shutdown();
        }
    }

    @Override // defpackage.o4
    public final void S(e3 e3Var) {
        ThreadPoolExecutor threadPoolExecutor = this.l;
        try {
            this.k.S(e3Var);
        } finally {
            threadPoolExecutor.shutdown();
        }
    }
}
