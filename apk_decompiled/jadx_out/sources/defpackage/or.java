package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class or extends qr {
    public final pc g;
    public final /* synthetic */ sr h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public or(sr srVar, long j, pc pcVar) {
        super(j);
        this.h = srVar;
        this.g = pcVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.g.H(this.h, x31.a);
    }

    @Override // defpackage.qr
    public final String toString() {
        return super.toString() + this.g;
    }
}
