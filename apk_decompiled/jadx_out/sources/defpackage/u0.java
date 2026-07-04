package defpackage;

import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class u0 extends s0 {
    public static u0 e;
    public static final aq0 f = aq0.f;
    public static final aq0 g = aq0.e;
    public h11 c;
    public su0 d;

    @Override // defpackage.s0
    public final int[] a(int i) {
        int i2;
        if (c().length() > 0 && i < c().length()) {
            try {
                su0 su0Var = this.d;
                if (su0Var != null) {
                    wo0 g2 = su0Var.g();
                    int round = Math.round(g2.d - g2.b);
                    if (i <= 0) {
                        i = 0;
                    }
                    h11 h11Var = this.c;
                    if (h11Var != null) {
                        int a = h11Var.a(i);
                        h11 h11Var2 = this.c;
                        if (h11Var2 != null) {
                            float d = h11Var2.d(a) + round;
                            h11 h11Var3 = this.c;
                            if (h11Var3 != null) {
                                float d2 = h11Var3.d(h11Var3.b.b - 1);
                                h11 h11Var4 = this.c;
                                if (d < d2) {
                                    if (h11Var4 != null) {
                                        i2 = h11Var4.b(d);
                                    } else {
                                        o20.G("layoutResult");
                                        throw null;
                                    }
                                } else if (h11Var4 != null) {
                                    i2 = h11Var4.b.b;
                                } else {
                                    o20.G("layoutResult");
                                    throw null;
                                }
                                return b(i, e(i2 - 1, g) + 1);
                            }
                            o20.G("layoutResult");
                            throw null;
                        }
                        o20.G("layoutResult");
                        throw null;
                    }
                    o20.G("layoutResult");
                    throw null;
                }
                o20.G("node");
                throw null;
            } catch (IllegalStateException unused) {
            }
        }
        return null;
    }

    @Override // defpackage.s0
    public final int[] d(int i) {
        int i2;
        if (c().length() <= 0 || i <= 0) {
            return null;
        }
        try {
            su0 su0Var = this.d;
            if (su0Var != null) {
                wo0 g2 = su0Var.g();
                int round = Math.round(g2.d - g2.b);
                int length = c().length();
                if (length <= i) {
                    i = length;
                }
                h11 h11Var = this.c;
                if (h11Var != null) {
                    int a = h11Var.a(i);
                    h11 h11Var2 = this.c;
                    if (h11Var2 != null) {
                        float d = h11Var2.d(a) - round;
                        if (d > 0.0f) {
                            h11 h11Var3 = this.c;
                            if (h11Var3 != null) {
                                i2 = h11Var3.b(d);
                            } else {
                                o20.G("layoutResult");
                                throw null;
                            }
                        } else {
                            i2 = 0;
                        }
                        if (i == c().length() && i2 < a) {
                            i2++;
                        }
                        return b(e(i2, f), i);
                    }
                    o20.G("layoutResult");
                    throw null;
                }
                o20.G("layoutResult");
                throw null;
            }
            o20.G("node");
            throw null;
        } catch (IllegalStateException unused) {
            return null;
        }
    }

    public final int e(int i, aq0 aq0Var) {
        h11 h11Var = this.c;
        if (h11Var != null) {
            int c = h11Var.c(i);
            h11 h11Var2 = this.c;
            if (h11Var2 != null) {
                aq0 e2 = h11Var2.e(c);
                h11 h11Var3 = this.c;
                if (aq0Var != e2) {
                    if (h11Var3 != null) {
                        return h11Var3.c(i);
                    }
                    o20.G("layoutResult");
                    throw null;
                }
                if (h11Var3 != null) {
                    xd0 xd0Var = h11Var3.b;
                    xd0Var.b(i);
                    ArrayList arrayList = (ArrayList) xd0Var.e;
                    t5 t5Var = ((yj0) arrayList.get(m20.s(i, arrayList))).a;
                    return (t5Var.d.e(i - r4.d) + r4.b) - 1;
                }
                o20.G("layoutResult");
                throw null;
            }
            o20.G("layoutResult");
            throw null;
        }
        o20.G("layoutResult");
        throw null;
    }
}
