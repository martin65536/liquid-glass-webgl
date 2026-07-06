package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class gy implements ky {
    public final long b = se.b(se.c, 0.38f);
    public final int c = 3;

    @Override // defpackage.ky
    public final long a() {
        return this.b;
    }

    @Override // defpackage.ky
    public final h6 b(b50 b50Var, aw0 aw0Var, sr0 sr0Var) {
        sr0Var.getClass();
        if (y20.n()) {
            h6 r = sr0Var.r("Ambient", "\nuniform float2 size;\nuniform float4 cornerRadii;\nuniform float angle;\nuniform float falloff;\n\n\nfloat radiusAt(float2 coord, float4 radii) {\n    if (coord.x >= 0.0) {\n        if (coord.y <= 0.0) return radii.y;\n        else return radii.z;\n    } else {\n        if (coord.y <= 0.0) return radii.x;\n        else return radii.w;\n    }\n}\n\nfloat sdRoundedRect(float2 coord, float2 halfSize, float radius) {\n    float2 cornerCoord = abs(coord) - (halfSize - float2(radius));\n    float outside = length(max(cornerCoord, 0.0)) - radius;\n    float inside = min(max(cornerCoord.x, cornerCoord.y), 0.0);\n    return outside + inside;\n}\n\nfloat2 gradSdRoundedRect(float2 coord, float2 halfSize, float radius) {\n    float2 cornerCoord = abs(coord) - (halfSize - float2(radius));\n    if (cornerCoord.x >= 0.0 || cornerCoord.y >= 0.0) {\n        return sign(coord) * normalize(max(cornerCoord, 0.0));\n    } else {\n        float gradX = step(cornerCoord.y, cornerCoord.x);\n        return sign(coord) * float2(gradX, 1.0 - gradX);\n    }\n}\n\nhalf4 main(float2 coord) {\n    float2 halfSize = size * 0.5;\n    float2 centeredCoord = coord - halfSize;\n    float radius = radiusAt(coord, cornerRadii);\n    \n    float gradRadius = min(radius * 1.5, min(halfSize.x, halfSize.y));\n    float2 grad = gradSdRoundedRect(centeredCoord, halfSize, gradRadius);\n    float2 normal = float2(cos(angle), sin(angle));\n    float d = dot(grad, normal);\n    float intensity = pow(abs(d), falloff);\n    float t = step(0.0, d);\n    return half4(t, t, t, 1.0) * intensity;\n}");
            r.a.setFloatUniform("size", Float.intBitsToFloat((int) (b50Var.j() >> 32)), Float.intBitsToFloat((int) (b50Var.j() & 4294967295L)));
            float b = mw0.b(b50Var.j()) / 2.0f;
            float[] fArr = new float[4];
            for (int i = 0; i < 4; i++) {
                fArr[i] = b;
            }
            r.a.setFloatUniform("cornerRadii", fArr);
            r.a.setFloatUniform("angle", 0.7853982f);
            r.a.setFloatUniform("falloff", 1.0f);
            return r;
        }
        return null;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof gy) || Float.compare(0.38f, 0.38f) != 0) {
                return false;
            }
            return true;
        }
        return true;
    }

    public final int hashCode() {
        return Float.floatToIntBits(0.38f);
    }

    public final String toString() {
        return "Ambient(intensity=0.38)";
    }

    @Override // defpackage.ky
    public final int u() {
        return this.c;
    }
}
