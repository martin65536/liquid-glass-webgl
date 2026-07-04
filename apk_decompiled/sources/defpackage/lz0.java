package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class lz0 extends ew0 implements iy0 {
    @Override // defpackage.iy0
    public final Object getValue() {
        Integer valueOf;
        synchronized (this) {
            Object[] objArr = this.l;
            objArr.getClass();
            valueOf = Integer.valueOf(((Number) objArr[((int) ((this.m + ((int) ((o() + this.o) - this.m))) - 1)) & (objArr.length - 1)]).intValue());
        }
        return valueOf;
    }

    public final void w(int i) {
        synchronized (this) {
            Object[] objArr = this.l;
            objArr.getClass();
            q(Integer.valueOf(((Number) objArr[((int) ((this.m + ((int) ((o() + this.o) - this.m))) - 1)) & (objArr.length - 1)]).intValue() + i));
        }
    }
}
