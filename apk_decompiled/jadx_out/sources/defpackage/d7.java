package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class d7 implements hy0 {
    public final c4 e;
    public final ik0 f;
    public i7 g;
    public long h;
    public long i;
    public boolean j;

    public d7(c4 c4Var, Object obj, i7 i7Var, long j, long j2, boolean z) {
        i7 i7Var2;
        this.e = c4Var;
        this.f = n30.B(obj);
        if (i7Var != null) {
            i7Var2 = dl.p(i7Var);
        } else {
            i7Var2 = (i7) ((gv) c4Var.f).e(obj);
            i7Var2.d();
        }
        this.g = i7Var2;
        this.h = j;
        this.i = j2;
        this.j = z;
    }

    @Override // defpackage.hy0
    public final Object getValue() {
        return this.f.getValue();
    }

    public final String toString() {
        return "AnimationState(value=" + this.f.getValue() + ", velocity=" + ((gv) this.e.g).e(this.g) + ", isRunning=" + this.j + ", lastFrameTimeNanos=" + this.h + ", finishedTimeNanos=" + this.i + ')';
    }

    public /* synthetic */ d7(c4 c4Var, Object obj, i7 i7Var, int i) {
        this(c4Var, obj, (i & 4) != 0 ? null : i7Var, Long.MIN_VALUE, Long.MIN_VALUE, false);
    }
}
