package defpackage;

import java.util.Arrays;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class pu implements s41 {
    public int a;
    public Object b;

    public pu(a31 a31Var, int i) {
        this.b = a31Var;
        this.a = i;
    }

    public void b(long j) {
        if (!g(j)) {
            int i = this.a;
            long[] jArr = (long[]) this.b;
            if (i >= jArr.length) {
                jArr = Arrays.copyOf(jArr, Math.max(i + 1, jArr.length * 2));
                this.b = jArr;
            }
            jArr[i] = j;
            if (i >= this.a) {
                this.a = i + 1;
            }
        }
    }

    @Override // defpackage.s41
    public i7 c(long j, i7 i7Var, i7 i7Var2, i7 i7Var3) {
        return ((e3) this.b).c(j, i7Var, i7Var2, i7Var3);
    }

    @Override // defpackage.s41
    public i7 d(long j, i7 i7Var, i7 i7Var2, i7 i7Var3) {
        return ((e3) this.b).d(j, i7Var, i7Var2, i7Var3);
    }

    @Override // defpackage.s41
    public i7 e(i7 i7Var, i7 i7Var2, i7 i7Var3) {
        return c(f(i7Var, i7Var2, i7Var3), i7Var, i7Var2, i7Var3);
    }

    @Override // defpackage.s41
    public long f(i7 i7Var, i7 i7Var2, i7 i7Var3) {
        return this.a * 1000000;
    }

    public boolean g(long j) {
        int i = this.a;
        for (int i2 = 0; i2 < i; i2++) {
            if (((long[]) this.b)[i2] == j) {
                return true;
            }
        }
        return false;
    }

    public void h(long j) {
        int i = this.a;
        int i2 = 0;
        while (i2 < i) {
            if (j == ((long[]) this.b)[i2]) {
                int i3 = this.a - 1;
                while (i2 < i3) {
                    long[] jArr = (long[]) this.b;
                    int i4 = i2 + 1;
                    jArr[i2] = jArr[i4];
                    i2 = i4;
                }
                this.a--;
                return;
            }
            i2++;
        }
    }

    @Override // defpackage.s41
    public /* synthetic */ void a() {
    }
}
