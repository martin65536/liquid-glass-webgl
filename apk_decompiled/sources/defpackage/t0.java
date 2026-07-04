package defpackage;

import java.text.BreakIterator;
import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class t0 extends s0 {
    public static t0 e;
    public static t0 f;
    public static t0 g;
    public static final aq0 h = aq0.f;
    public static final aq0 i = aq0.e;
    public final /* synthetic */ int c;
    public Object d;

    public /* synthetic */ t0(int i2) {
        this.c = i2;
    }

    @Override // defpackage.s0
    public final int[] a(int i2) {
        int i3;
        switch (this.c) {
            case 0:
                int length = c().length();
                if (length <= 0 || i2 >= length) {
                    return null;
                }
                if (i2 < 0) {
                    i2 = 0;
                }
                do {
                    BreakIterator breakIterator = (BreakIterator) this.d;
                    if (breakIterator != null) {
                        boolean isBoundary = breakIterator.isBoundary(i2);
                        BreakIterator breakIterator2 = (BreakIterator) this.d;
                        if (!isBoundary) {
                            if (breakIterator2 != null) {
                                i2 = breakIterator2.following(i2);
                            } else {
                                o20.G("impl");
                                throw null;
                            }
                        } else {
                            if (breakIterator2 != null) {
                                int following = breakIterator2.following(i2);
                                if (following == -1) {
                                    return null;
                                }
                                return b(i2, following);
                            }
                            o20.G("impl");
                            throw null;
                        }
                    } else {
                        o20.G("impl");
                        throw null;
                    }
                } while (i2 != -1);
                return null;
            case 1:
                if (c().length() <= 0 || i2 >= c().length()) {
                    return null;
                }
                if (i2 < 0) {
                    i2 = 0;
                }
                while (!h(i2) && (!h(i2) || (i2 != 0 && h(i2 - 1)))) {
                    BreakIterator breakIterator3 = (BreakIterator) this.d;
                    if (breakIterator3 != null) {
                        i2 = breakIterator3.following(i2);
                        if (i2 == -1) {
                            return null;
                        }
                    } else {
                        o20.G("impl");
                        throw null;
                    }
                }
                BreakIterator breakIterator4 = (BreakIterator) this.d;
                if (breakIterator4 != null) {
                    int following2 = breakIterator4.following(i2);
                    if (following2 == -1 || !g(following2)) {
                        return null;
                    }
                    return b(i2, following2);
                }
                o20.G("impl");
                throw null;
            default:
                if (c().length() <= 0 || i2 >= c().length()) {
                    return null;
                }
                h11 h11Var = (h11) this.d;
                aq0 aq0Var = h;
                if (i2 < 0) {
                    if (h11Var != null) {
                        i3 = h11Var.a(0);
                    } else {
                        o20.G("layoutResult");
                        throw null;
                    }
                } else if (h11Var != null) {
                    int a = h11Var.a(i2);
                    if (e(a, aq0Var) == i2) {
                        i3 = a;
                    } else {
                        i3 = a + 1;
                    }
                } else {
                    o20.G("layoutResult");
                    throw null;
                }
                h11 h11Var2 = (h11) this.d;
                if (h11Var2 != null) {
                    if (i3 >= h11Var2.b.b) {
                        return null;
                    }
                    return b(e(i3, aq0Var), e(i3, i) + 1);
                }
                o20.G("layoutResult");
                throw null;
        }
    }

    @Override // defpackage.s0
    public final int[] d(int i2) {
        int i3;
        switch (this.c) {
            case 0:
                int length = c().length();
                if (length <= 0 || i2 <= 0) {
                    return null;
                }
                if (i2 > length) {
                    i2 = length;
                }
                do {
                    BreakIterator breakIterator = (BreakIterator) this.d;
                    if (breakIterator != null) {
                        boolean isBoundary = breakIterator.isBoundary(i2);
                        BreakIterator breakIterator2 = (BreakIterator) this.d;
                        if (!isBoundary) {
                            if (breakIterator2 != null) {
                                i2 = breakIterator2.preceding(i2);
                            } else {
                                o20.G("impl");
                                throw null;
                            }
                        } else {
                            if (breakIterator2 != null) {
                                int preceding = breakIterator2.preceding(i2);
                                if (preceding == -1) {
                                    return null;
                                }
                                return b(preceding, i2);
                            }
                            o20.G("impl");
                            throw null;
                        }
                    } else {
                        o20.G("impl");
                        throw null;
                    }
                } while (i2 != -1);
                return null;
            case 1:
                int length2 = c().length();
                if (length2 <= 0 || i2 <= 0) {
                    return null;
                }
                if (i2 > length2) {
                    i2 = length2;
                }
                while (i2 > 0 && !h(i2 - 1) && !g(i2)) {
                    BreakIterator breakIterator3 = (BreakIterator) this.d;
                    if (breakIterator3 != null) {
                        i2 = breakIterator3.preceding(i2);
                        if (i2 == -1) {
                            return null;
                        }
                    } else {
                        o20.G("impl");
                        throw null;
                    }
                }
                BreakIterator breakIterator4 = (BreakIterator) this.d;
                if (breakIterator4 != null) {
                    int preceding2 = breakIterator4.preceding(i2);
                    if (preceding2 == -1 || !h(preceding2)) {
                        return null;
                    }
                    if (preceding2 != 0 && h(preceding2 - 1)) {
                        return null;
                    }
                    return b(preceding2, i2);
                }
                o20.G("impl");
                throw null;
            default:
                if (c().length() <= 0 || i2 <= 0) {
                    return null;
                }
                int length3 = c().length();
                h11 h11Var = (h11) this.d;
                aq0 aq0Var = i;
                if (i2 > length3) {
                    if (h11Var != null) {
                        i3 = h11Var.a(c().length());
                    } else {
                        o20.G("layoutResult");
                        throw null;
                    }
                } else if (h11Var != null) {
                    int a = h11Var.a(i2);
                    if (e(a, aq0Var) + 1 == i2) {
                        i3 = a;
                    } else {
                        i3 = a - 1;
                    }
                } else {
                    o20.G("layoutResult");
                    throw null;
                }
                if (i3 < 0) {
                    return null;
                }
                return b(e(i3, h), e(i3, aq0Var) + 1);
        }
    }

    public int e(int i2, aq0 aq0Var) {
        h11 h11Var = (h11) this.d;
        if (h11Var != null) {
            int c = h11Var.c(i2);
            h11 h11Var2 = (h11) this.d;
            if (h11Var2 != null) {
                aq0 e2 = h11Var2.e(c);
                h11 h11Var3 = (h11) this.d;
                if (aq0Var != e2) {
                    if (h11Var3 != null) {
                        return h11Var3.c(i2);
                    }
                    o20.G("layoutResult");
                    throw null;
                }
                if (h11Var3 != null) {
                    xd0 xd0Var = h11Var3.b;
                    xd0Var.b(i2);
                    ArrayList arrayList = (ArrayList) xd0Var.e;
                    t5 t5Var = ((yj0) arrayList.get(m20.s(i2, arrayList))).a;
                    return (t5Var.d.e(i2 - r4.d) + r4.b) - 1;
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

    public void f(String str) {
        switch (this.c) {
            case 0:
                this.a = str;
                BreakIterator breakIterator = (BreakIterator) this.d;
                if (breakIterator != null) {
                    breakIterator.setText(str);
                    return;
                } else {
                    o20.G("impl");
                    throw null;
                }
            default:
                this.a = str;
                BreakIterator breakIterator2 = (BreakIterator) this.d;
                if (breakIterator2 != null) {
                    breakIterator2.setText(str);
                    return;
                } else {
                    o20.G("impl");
                    throw null;
                }
        }
    }

    public boolean g(int i2) {
        if (i2 > 0 && h(i2 - 1)) {
            if (i2 == c().length() || !h(i2)) {
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean h(int i2) {
        if (i2 >= 0 && i2 < c().length()) {
            return Character.isLetterOrDigit(c().codePointAt(i2));
        }
        return false;
    }
}
