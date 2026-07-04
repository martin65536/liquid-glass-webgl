package defpackage;

import java.util.concurrent.CancellationException;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class a extends CancellationException {
    public final transient Object e;

    public a(ps psVar) {
        super("Flow was aborted, no more elements needed");
        this.e = psVar;
    }

    @Override // java.lang.Throwable
    public final Throwable fillInStackTrace() {
        setStackTrace(new StackTraceElement[0]);
        return this;
    }
}
